# ImmersionBar -- android 4.4以上沉浸式实现 
## 使用 
> android studio
   ```groovy
   // 基础依赖包，必须要依赖
   implementation 'com.alliky.immersionbar:immersionbar:3.0.0'
   ```


## 关于使用AndroidX支持库
- 如果你的项目中使用了AndroidX支持库，请在你的gradle.properties加入如下配置，如果已经配置了，请忽略
    ```groovy
       android.useAndroidX=true
       android.enableJetifier=true
    ```

## 关于全面屏与刘海
#### 关于全面屏
   在manifest加入如下配置，四选其一，或者都写
   
   ① 在manifest的Application节点下加入
   ```xml
      <meta-data 
        android:name="android.max_aspect"
        android:value="2.4" />
   ```
   ② 在manifest的Application节点中加入
   ```xml
      android:resizeableActivity="true"
   ```
   ③ 在manifest的Application节点中加入
   ```xml
      android:maxAspectRatio="2.4"
   ```
   ④ 升级targetSdkVersion为25以上版本
   
#### 关于刘海屏 
  在manifest的Application节点下加入，vivo和oppo没有找到相关配置信息
   ```xml
      <!--适配华为（huawei）刘海屏-->
      <meta-data 
        android:name="android.notch_support" 
        android:value="true"/>
      <!--适配小米（xiaomi）刘海屏-->
      <meta-data
        android:name="notch.config"
        android:value="portrait|landscape" />
   ```
  
## Api详解
- 基础用法

    ```java
    StatusBarUtils.with(this).init();
    ```
- 高级用法(每个参数的意义)

    ```java
     StatusBarUtils.with(this)
                 .transparentStatusBar()  //透明状态栏，不写默认透明色
                 .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                 .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                 .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                 .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
                 .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                 .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
                 .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
                 .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
                 .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                 .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                 .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                 .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                 .autoNavigationBarDarkModeEnable(true,0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                 .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
                 .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                 .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
                 .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
                 .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
                 .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
                 .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
                 .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                 .supportActionBar(true) //支持ActionBar使用
                 .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
                 .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
                 .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
                 .removeSupportView(toolbar)  //移除指定view支持
                 .removeSupportAllView() //移除全部view支持
                 .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                 .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.x手机导航栏颜色，默认为true
                 .navigationBarWithEMUI3Enable(true) //是否可以修改emui3.x手机导航栏颜色，默认为true
                 .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                 .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                 .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调，keyboardEnable为true才会回调此方法
                       @Override
                       public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                           LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
                       }
                  })
                 .setOnNavigationBarListener(onNavigationBarListener) //导航栏显示隐藏监听，目前只支持华为和小米手机
                 .setOnBarListener(OnBarListener) //第一次调用和横竖屏切换都会触发，可以用来做刘海屏遮挡布局控件的问题
                 .addTag("tag")  //给以上设置的参数打标记
                 .getTag("tag")  //根据tag获得沉浸式参数
                 .reset()  //重置所以沉浸式参数
                 .init();  //必须调用方可应用以上所配置的参数
    ```
## 在Activity中实现沉浸式

- java用法

   ```java
    StatusBarUtils.with(this).init();
   ```
- kotlin用法
 
   ```kotlin
    statusBarUtils {
        statusBarColor(R.color.colorPrimary) 
        navigationBarColor(R.color.colorPrimary)
    }
   ```
  

## 在PopupWindow中实现沉浸式，具体实现参考demo
   重点是调用以下方法，但是此方法会导致有导航栏的手机底部布局会被导航栏覆盖，还有底部输入框无法根据软键盘弹出而弹出，具体适配请参考demo。
   ```java
       popupWindow.setClippingEnabled(false);
   ```

## 状态栏与布局顶部重叠解决方案，六种方案根据不同需求任选其一
- ① 使用dimen自定义状态栏高度，不建议使用，因为设备状态栏高度并不是固定的

    在values-v19/dimens.xml文件下
    ```xml
        <dimen name="status_bar_height">25dp</dimen>
     ```
    
    在values/dimens.xml文件下     
    ```xml
        <dimen name="status_bar_height">0dp</dimen>
    ```
    
    然后在布局界面添加view标签，高度指定为status_bar_height
    ```xml
       <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:background="@color/darker_gray"
           android:orientation="vertical">
       
           <View
               android:layout_width="match_parent"
               android:layout_height="@dimen/status_bar_height"
               android:background="@color/colorPrimary" />
       
           <android.support.v7.widget.Toolbar
               android:layout_width="match_parent"
               android:layout_height="wrap_content"
               android:background="@color/colorPrimary"
               app:title="方法一"
               app:titleTextColor="@android:color/white" />
       </LinearLayout>
    ```
  
- ② 使用系统的fitsSystemWindows属性，使用该属性不会导致输入框与软键盘冲突问题，不要再Fragment使用该属性，只适合纯色状态栏

   ```xml
       <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:orientation="vertical"
           android:fitsSystemWindows="true">
       </LinearLayout>
   ```
   然后使用StatusBarUtils时候必须指定状态栏颜色
   ```java
       StatusBarUtils.with(this)
            .statusBarColor(R.color.colorPrimary)
            .init();
   ```
   - 注意：StatusBarUtils一定要在设置完布局以后使用，

- ③ 使用StatusBarUtils的fitsSystemWindows(boolean fits)方法，只适合纯色状态栏

    ```java
        StatusBarUtils.with(this)
            .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
            .statusBarColor(R.color.colorPrimary)
            .init();
    ```
- ④ 使用StatusBarUtils的statusBarView(View view)方法，可以用来适配渐变色状态栏、侧滑返回

    在标题栏的上方增加View标签，高度指定为0dp
    ```xml
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
               xmlns:app="http://schemas.android.com/apk/res-auto"
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:background="@color/darker_gray"
               android:orientation="vertical">
           
               <View
                   android:layout_width="match_parent"
                   android:layout_height="0dp"
                   android:background="@color/colorPrimary" />
           
               <android.support.v7.widget.Toolbar
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content"
                   android:background="@color/colorPrimary"
                   app:title="方法四"
                   app:titleTextColor="@android:color/white" />
        </LinearLayout>
     ```
      
    然后使用StatusBarUtils的statusBarView方法，指定view就可以啦
    ```java
         StatusBarUtils.with(this)
               .statusBarView(view)
               .init();
         //或者
         //StatusBarUtils.setStatusBarView(this,view);
     ```   
- ⑤ 使用StatusBarUtils的titleBar(View view)方法，原理是设置paddingTop，可以用来适配渐变色状态栏、侧滑返回
    ```java
             StatusBarUtils.with(this)
                   .titleBar(view) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                   .init();
             //或者
             //StatusBarUtils.setTitleBar(this, view);
     ```
- ⑥ 使用StatusBarUtils的titleBarMarginTop(View view)方法，原理是设置marginTop，只适合纯色状态栏
    ```java
             StatusBarUtils.with(this)
                   .titleBarMarginTop(view)  //可以为任意view
                   .statusBarColor(R.color.colorPrimary)  //指定状态栏颜色,根据情况是否设置
                   .init();
             //或者使用静态方法设置
             //StatusBarUtils.setTitleBarMarginTop(this,view);
     ```
       
## 解决EditText和软键盘的问题

 - 第一种方案
   ```java
       StatusBarUtils.with(this)
                   .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
               //  .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
               //                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //软键盘自动弹出
                   .init();
   ```
 - 第二种方案
   不使用keyboardEnable方法，只需要在布局的根节点（最外层节点）加上android:fitsSystemWindows="true"属性即可，只适合纯色状态栏
    


## 当白色背景状态栏遇到不能改变状态栏字体为深色的设备时，解决方案
   ```java
         StatusBarUtils.with(this)
                     .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                     .init();
   ```
    
## 状态栏和导航栏其它方法
	
- public static boolean hasNavigationBar(Activity activity)
 
    判断是否存在导航栏
    
- public static int getNavigationBarHeight(Activity activity)
 
    获得导航栏的高度
 
- public static int getNavigationBarWidth(Activity activity)
 
    获得导航栏的宽度
    
- public static boolean isNavigationAtBottom(Activity activity)
 
    判断导航栏是否在底部
    
- public static int getStatusBarHeight(Activity activity)
 
    获得状态栏的高度
    
- public static int getActionBarHeight(Activity activity)
 
    获得ActionBar的高度

- public static boolean hasNotchScreen(Activity activity)
 
    是否是刘海屏
    
- public static boolean getNotchHeight(Activity activity)
 
    获得刘海屏高度
    
- public static boolean isSupportStatusBarDarkFont()
 
    判断当前设备支不支持状态栏字体设置为黑色

- public static boolean isSupportNavigationIconDark()
 
    判断当前设备支不支持导航栏图标设置为黑色

- public static void hideStatusBar(Window window) 
 
    隐藏状态栏
    
    
## 混淆规则(proguard-rules.pro)
   ```
    -keep class  com.alliky.statusbar.* {*;} 
    -dontwarn  com.alliky.statusbar.**
   ```
   
