# WebViewPhoneAndVideo
最近在开发过程中遇到一个问题,主要是调用第三方的实名认证,需要拍照和录像;

办过支付宝大宝卡和腾讯的大王卡的都知道这玩意,办卡的时候就需要进行实名认证,人脸识别;

本来第三方平台(xxx流量公司)说的是直接用WebView加载这个H5界面就完事了,我心想这么简单,那不是分分钟的事,放着后面做(公司就我一个安卓,所以开发都是我说的算^_^,独立开发有的时候还是挺爽);

结果到项目快要上线的时候,只想说一句mmp,根本调不了相机,这个时候怎么搞,只有自己去实现了,才发现自己已经进了webview的深坑;

到处找资料,发现webview根本不能让h5自己调用,ios是可以的,项目经理就说是我的锅,真特么又一句mmp(关键是这个H5还特么不能改,不能提供给我调用的方法);

进入正题,首先来了解webview,这里我分享两篇大佬的博客 
1,WebView开车指南 
2,WebView详解 
看完这两篇基本你已经可以随意操作webview了, 但是,当你调用相机的时候你才发现这只是入坑的开始

第一个坑

调用相机之后,文件回调不了,其实根本就是没有回调.这个时候你要去检查你的webview的Settings了关键代码,是否给足了权限;

   WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
其中settings.setDomStorageEnabled(true);这个方法当时我没加,血崩了一次;

第二个坑

WebChromeClient的openFileChooser()只调用了一次
1
2
首先了解为什么这个方法只调用了一次,看这篇博客就可 
Android开发深入理解WebChromeClient之onShowFileChooser或openFileChooser使用说明 
看了他基本你就了解怎么回事了

第三个坑 
怎么区分是要调用相机是需要拍照还是录视频 
这个时候你就要看WebViewClient的 shouldOverrideUrlLoading()方法了,我是通过拦截url来判断url里面是拍照还是录制视频来加一个flag 
这样你调用你的相机的时候就可以分别处理了

第四个坑 
android 7.0的FileProvider的坑 
看洪阳大佬的这篇博客基本就了解了 
Android 7.0 行为变更 通过FileProvider在应用间共享文件吧
