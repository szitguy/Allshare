AllShare
=======

封装常用分享平台的sdk，统一并简化各分享sdk使用，提升分享功能的开发速度

特点：

1. 简化各平台sdk集成，只需要引入一个gradle插件，然后配置相应平台的appid即可开始使用
2. 使用简单，类命名统一，见名知意
3. 各平台分享结果由统一的接口进行回调，结果简化为成功、失败、取消

说明：目前只支持了`微信`和`QQ`两个平台的分享

集成
-----

### 1. 添加AllShare Gradle插件（插件中已处理AllShare库的依赖添加）

```
repositories {
    jcenter()
}

dependencies {
    classpath 'com.android.tools.build:gradle:2.3.0'
    classpath 'cn.itguy:allshare-gradle-plugin:latest.release' // latest release is 1.0.7
}
```

### 2. 配置各分享平台appId

以应用包名和appId对应的形式进行配置，可配多个，如：

```
// AllShare配置
AllSharePlugin {
    // qqAppId配置
    qqAppIdMap = [
        "应用包名1": "qqAppId1",
        "应用包名2": "qqAppId2",
    ]
    // wxAppId配置
    wxAppIdMap = [
        "应用包名1": "wxAppId1",
        "应用包名2": "wxAppId2",
    ]
}
```

使用
-----

> 代码片段取自sample

### 1. 初始化

建议在应用的Application子类中初始化，如：

```
AllShare.init(applicationContext);
```

### 2. 统一的结果回调

实现`cn.itguy.allshare.Callback`接口，如：

```
private static class ShareCallback implements Callback {

    WeakReference<Context> contextWeakRef;

    public ShareCallback(Context context) {
        contextWeakRef = new WeakReference<>(context);
    }

    @Override public void onSuccess() {
        Context context = contextWeakRef.get();
        if (null != context) {

            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onFailed() {
        Context context = contextWeakRef.get();
        if (null != context) {

            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public void onCanceled() {
        Context context = contextWeakRef.get();
        if (null != context) {

            Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
        }
    }
}
```

### 3. 微信分享

#### 分享文本

```
WXContent content = new WXContent();
content.setType(Content.Type.TEXT);
content.setTimeline(momentCheckBox.isChecked()); // 是否分享到朋友圈
content.setText(String.format("%s --- %s", ALL_SHARE_GITHUB, TITLE)); // 设置文本内容
content.setDescription(DESC); // 设置描述
AllShare.share(activityContext, content, new ShareCallback(this));
```

#### 分享链接

```
WXContent content = new WXContent();
content.setType(Content.Type.LINK);
content.setTimeline(momentCheckBox.isChecked()); // 是否分享到朋友圈
content.setTitle(TITLE); // 设置标题
content.setDescription(DESC); // 设置描述
content.setLink(ALL_SHARE_GITHUB); // 设置链接
content.setThumb(icon); // 设置缩略图
AllShare.share(activityContext, content, new ShareCallback(this));
```

#### 分享图片

```
WXContent content = new WXContent();
content.setType(Content.Type.IMAGE);
content.setTimeline(momentCheckBox.isChecked()); // 是否分享到朋友圈
content.setImage(icon); // 设置图片
content.setThumb(icon); // 设置缩略图
AllShare.share(activityContext, content, new ShareCallback(this));
```

### 4. QQ分享

#### 分享链接

```
QQContent content = new QQContent();
content.setType(Content.Type.LINK);
content.setImageUrl(ICON_FILE.getAbsolutePath()); // 本地or网络图片，可选
content.setQzone(qzoneCheckBox.isChecked()); // 是否分享到qq空间
content.setTitle(TITLE); // 设置标题
content.setTargetUrl(ALL_SHARE_GITHUB); // 设置链接
content.setSummary(DESC); // 设置描述，可选
AllShare.share(activityContext, content, new ShareCallback(this));
```

#### 分享图片

```
if (qzoneCheckBox.isChecked()) {
    Toast.makeText(this, "Qzone不支持图片分享", Toast.LENGTH_SHORT).show();
    return;
}
QQContent content = new QQContent();
content.setType(Content.Type.IMAGE);
content.setImageUrl(ICON_FILE.getAbsolutePath()); // 必须为本地图片
AllShare.share(activityContext, content, new ShareCallback(this));
```

License
-----
    MIT License

    Copyright (c) 2017 Martin

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
