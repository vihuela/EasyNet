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
   

    /**
         * retrofit2原始用法
         */
        private void before1() {
            Call<NewsResponseOrigin> originResponseCall = MyNet.get().getNewsOrigin("普京", "20f453107e7739c9a363edb7507bd0ed");
            originResponseCall.enqueue(new Callback<NewsResponseOrigin>() {
                @Override public void onResponse(Call<NewsResponseOrigin> call, Response<NewsResponseOrigin> response) {
                    //数据code正确时
                    if (response.body() != null && response.body().error_code == 0) {
                        /**
                         * server api design is not a good return data redundancy may exist, we need to be screened
                         * 服务端api设计可能不良好，存在返回数据之后需要重新拼装筛选
                         */
                        List<NewsResponseOrigin.ResultEntity> result = response.body().result;
                        List<News> newsList = new ArrayList<>();
                        for (NewsResponseOrigin.ResultEntity entity : result) {
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

                @Override public void onFailure(Call<NewsResponseOrigin> call, Throwable t) {
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
            /**
             * 针对原始改进之后的用法
             */
            private void after1() {
                Call<NewsResponseUpdate> responseUpdate = MyNet.get().getNewsUpdate("普京", "20f453107e7739c9a363edb7507bd0ed");
                responseUpdate.enqueue(new MyNetCallBack<NewsResponseUpdate>("eventTag") {
                    @Override public void onSuccess(NewsResponseUpdate responseUpdate) {
                        //仅有效数据时才回调
                        List<NewsResponseUpdate.ResultEntity> result = responseUpdate.getResult();
                    }
        
                    @Override public void onDispatchError(Error error, ErrorInfo e) {
                        switch (error) {
                            case Internal:
                                Toast(e.getObject().toString());
                                break;
                            case Invalid:
                                /**
                                 * 有返回数据但是是无效的
                                 */
                                NewsResponseUpdate result = e.getObject();
        
                                break;
                            case NetWork:
                                Toast(e.getObject().toString());
                                break;
                            case Server:
                                Toast(e.getObject().toString());
                                break;
                            case UnKnow:
                                Toast(e.getObject().toString());
                                break;
                        }
                        /**
                         * 可以统一底层事件tag分发，也可以在这里具体判断
                         */
                        super.onDispatchError(error, e);
                    }
                });
            }
            
### requestLog ###
            12-08 07:41:54.625 9444-9493/com.hadlink.easynetsample D/you_tag_name: --------------REQUEST START------------
                                                                                   ---URL:http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=10&time=1418816972&key=d4ac32344201cd2e005c966e08271702 GET in 950.6ms
                                                                                   ---RES:http/1.1 200 OK
            12-08 07:41:54.625 9444-9493/com.hadlink.easynetsample D/you_tag_name: --------------REQUEST END--------------
# setup

    compile 'com.hadlink:easynet:1.2.5'
    
# changeLog
    
* v1.0.0 ：init commit
     
* v1.0.1 ：add header config
     
* v1.0.2 ~ v1.0.6 ：fix error dispatch bug

* v1.1.0 ：optimization success callback logic

* v1.2.1 ：update retrofit、OkHttp to last

* v1.2.3、v1.2.4 ：add event tag

* v1.2.5 ：merge ok3 Branch and Latest update references

    
# author

[vihuela](https://github.com/vihuela)  
[zhoumingliang](https://github.com/zhoumingliang)  

## Acknowledgments

 - [Retrofit2](http://square.github.io/retrofit) 
    
