**ForBestAndroid**
<br/>
<br/>
**本项目意在使用Android最流行的设计框架模式，主要使用的设计框架如下：**

1.采用解耦合的MVP设计模式

2.使用Rxjava2 + Retrofit2 + OkHttp3

3.使用Glide图片加载

4.使用美团的多环境多渠道框架【如果需要加固+热更新，建议使用360多渠道 + 360加固 + thinker】

5.TinkerPatch热修复框架

6.LeakCanary检测内存泄露

7.EventBus用于组件间通讯

**注意：所有第三方库需要加入混淆配置到proguard-rules.pro文件**

#资源文件命名
模块号+ 描述 + [ 类型（状态）]
##例如 
home_bg_img<br/> 
home_message_btn_img

#资源id命名
类型 + 模块 + 功能
##例如
iv_home_bg<br/> 
tv_home_message

#资源文字命名
模块 + 功能 + text

#资源颜色命名
1.通用
<br/>
颜色 + color
<br/>
<br/>
2.非通用<br/>
模块 + 功能 + 【颜色】 + color

##例如
home_title_text

#drawable文件命名
类型 + 模块 + 功能 + 状态<br/> 
##例如
selector_home_message_btn_normal<br/> 
selector_home_message_btn_pressed<br/> 
animation_home_message<br/> 