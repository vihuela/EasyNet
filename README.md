#EasyNetSample#

* 基于retrofit2，用法不变

    * 多了个自定义callBack
    * 多了个自定义基类请求
    
* 自定义请求，列表加载仅需要一个baseRequest搞定
* 基于两级泛型传递，直接得到解析对象
* 完善的请求log打印
* 准确的错误分发
* 统一回调Call与Observable



### First Step ###
> *In Application.onCreate()*
    
    
    ArrayMap<String, String> header = new ArrayMap<>();
            header.put("User-Agent", "android");
            /**
             * net config
             */
            final NetConfig netConfig = new NetConfigBuilder()
                    .context(this)
                    .log(true)
                    .logTag("you_tag_name")
                    .printResponseBody(true)
                    .header(header)
                    .createNetConfig();
    
            NetUtils.setNetConfig(netConfig);
    
### Second Step ###
> *config your baseResponse ( **implements CommonResponse** )*

    public class BaseList_1Response<T> implements CommonResponse<List<T>> {
        public String reason;
        public int error_code;
        public List<T> result;
    
        @Override public List<T> getResult() {
            return result;
        }
        @Override public void setResult(List<T> ts) {
            this.result = ts;
        }
        @Override public boolean isValid() {
            return error_code == 0;
        }
    }

### Three Step ###
> *every thing usage same as retrofit2*

### No next Step ###
## Before ##
   

    private void before() {
            Call<NewsResponse> originResponseCall = MyNet.get().getNews("普京", "20f453107e7739c9a363edb7507bd0ed");
            originResponseCall.enqueue(new Callback<NewsResponse>() {
                @Override public void onResponse(Response<NewsResponse> response, Retrofit retrofit) {
                    if (response.body() != null && response.body().error_code == 0) {
                        /**
                         * server api design is not a good return data redundancy may exist, we need to be screened
                         * 服务端api设计可能不良好，存在返回数据之后需要重新拼装筛选
                         */
                        List<NewsResponse.ResultEntity> result = response.body().result;
                        List<News> newsList = new ArrayList<>();
                        for (NewsResponse.ResultEntity entity : result) {
                            News news = new News();
                            news.content = entity.content;
                            news.full_title = entity.full_title;
                            news.img = entity.img;
                            //...
                            newsList.add(news);
                        }
                        //fill list to recycler/listView
    
                        /**
                         * There may be a whole lot like this App interface, that the above code should write again?
                         * 可能整个App存在很多这样子的接口，那不是以上代码都需要走一遍？
                         */
                    }
                }
    
                @Override public void onFailure(Throwable t) {
                    /**
                     * You need to judge for themselves what type of error
                     * 在retrofit2默认错误回调中，你需要自己去处理分析错误类型
                     */
                    if (t != null) {
                        if (t instanceof UnknownHostException || (t.getCause() != null && t.getCause() instanceof UnknownHostException)) {
                            //net error
                        } else {
                            //json parse error
                        }
                    }
    
                }
            });
        }

## After ##
        private void after1() {
                Call<BaseList_1Response<News>> responseCall = MyNet.get().getNews_("普京", "20f453107e7739c9a363edb7507bd0ed");
                responseCall.enqueue(new MyNetCallBack<BaseList_1Response<News>>() {
        
        
                    @Override public void onSuccess(BaseList_1Response<News> newsBaseList1Response) {
                        /**
                         * Direct access,NO need to be screened
                         * 直接拿到想要的bean对象，不需要筛选
                         */
                        List<News> result = newsBaseList1Response.getResult();
                        //
                    }
        
                    @Override public void onDispatchError(Error error, Object message) {
                        super.onDispatchError(error, message);
                        /**
                         * error is enum ，A clear understanding of the wrong type
                         * error是个枚举类型，可以自行判断
                         */
                        switch (error) {
                            case Internal:
                                break;
                            case NetWork:
                                break;
                        }
                    }
                });
            }
            
### requestLog ###
            12-08 07:41:54.625 9444-9493/com.hadlink.easynetsample D/you_tag_name: --------------REQUEST START------------
                                                                                   ---URL:http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=10&time=1418816972&key=d4ac32344201cd2e005c966e08271702 GET in 950.6ms
                                                                                   ---RES:http/1.1 200 OK
            12-08 07:41:54.625 9444-9493/com.hadlink.easynetsample D/you_tag_name: --------------REQUEST END--------------
# setup

    compile 'com.hadlink:easynet:1.1.0'
    
# changeLog
    
* v1.0.0 ：init commit
     
* v1.0.1 ：add header config
     
* v1.0.2 ~ v1.0.6 ：fix error dispatch bug

* v1.1.0 ：optimization success callback logic

    
# author

[vihuela](https://github.com/vihuela)  
[zhoumingliang](https://github.com/zhoumingliang)  

## Acknowledgments

 - [Retrofit2](http://square.github.io/retrofit) 
    
