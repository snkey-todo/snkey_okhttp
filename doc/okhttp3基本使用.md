# okhttp3基本使用

[官网地址](https://square.github.io/okhttp/)

## okhttp3基本使用

[okhttp3基本使用](https://www.jianshu.com/p/da4a806e599b)


## 异常

### 流关闭异常

异常信息如下：
```
java.lang.IllegalStateException: closed
```
相关代码如下：
```java
call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
                tv_result.setText(response.body().string());
            }
        });
```
问题出现在我在callback中调用了两次response.body().string()，导致了错误的出现！原因是流已经关闭，所以无法再进行操作。

[参考链接](https://blog.csdn.net/wen_haha/article/details/81018046)

其实上面的代码还有另外一处错误，不能在主线程中更新UI。

### 主线程更新UI

错误信息如下：
```
android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
```

代码如下，Callback回调的结果还是在子线程当中，所以无法直接进行UI更新操作。

```java
call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
                tv_result.setText(result);
            }
        });
```

修改后如下：

```java
call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(result);
                    }
                });

            }
        });
```

