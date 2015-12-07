# EasyNetSample

* 基于retrofit2，用法不变
* 基于两级泛型传递，直接得到解析对象
* 完善的请求log打印
* 准确的错误分发
* 统一回调Call与Observable



## set
    Application.onCreate():
    
        final NetConfig netConfig = new NetConfigBuilder()
                    .appContext(this)
                    .responseCacheDir("you_cache_dir_name")
                    .log(true)
                    .logTag("you_tag_name")
                    .printResponseBody(false)
                    .createNetConfig();
    
            NetUtils.setNetConfig(netConfig);
        
## invoke

    private void before() {
        final Call<TestResponse> waitReplyList = MyNet.get().getWaitReplyList1(107, 1, 20);
        waitReplyList.enqueue(new Callback<TestResponse>() {
            @Override public void onResponse(Response<TestResponse> response, Retrofit retrofit) {
                System.out.println();
            }

            @Override public void onFailure(Throwable t) {

            }
        });

    }
    private void after() {
        final Call<BaseListResponse<WaitingAskBean>> waitReplyList = MyNet.get().getWaitReplyList(107, 1, 20);
        waitReplyList.enqueue(new MyNetCallBack<BaseListResponse<WaitingAskBean>>() {
            @Override public void onSuccess(BaseListResponse<WaitingAskBean> waitingAskBeanBaseListResponse) {
                System.out.println();//debug check
            }
        });

    }

##check demo please
