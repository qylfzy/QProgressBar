# 自定义控件QProgressBar

## 效果图
![效果图](https://github.com/qylfzy/QProgressBar/blob/master/qprogressbar.gif)

## QprogressBar共有三种形式

### 直线型progress
~~~
<com.qiyou.qprogressbars.QProgressBar
        android:id="@+id/l_progressbar"
        android:layout_width="match_parent"
        android:layout_height="20dp"
        app:bar_type="linesBar" />
~~~

### 圆形progress
~~~
<com.qiyou.qprogressbars.QProgressBar
        android:id="@+id/c_progressbar"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginTop="10dp"
        app:bar_type="circleBar" />
~~~

### 圆环形progress
~~~
<com.qiyou.qprogressbars.QProgressBar
        android:id="@+id/a_progressbar"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginTop="10dp"
        app:bar_annulus_border_width="40"
        app:bar_type="annulusBar" />
~~~

## 属性设置
~~~ 
        //progresebar 最大值 默认为100
        app:bar_max="100"
        
        //progressbar 背景颜色 默认黑色
        app:bar_bg_color="@color/colorPrimary"
        
        //progressbar progress背景颜色 默认红色
        app:bar_pr_color="@color/colorPrimaryDark"
        
        //progressbar类型 linesBar直线 circleBar圆形 annulusBar圆环 默认为直线
        app:bar_type="linesBar"
        
        //progressbar 进度值颜色 默认白色
        app:bar_text_color="@color/colorAccent"
        
        //progressbar 进度值大小 默认30
        app:bar_text_size="20"
        
        //progressbar 是否显示进度值 默认为true
        app:bar_text_visibily="true"
        
        //progressbar 圆环宽度 该属性只对圆环progressbar有效
        app:bar_annulus_border_width="10"
~~~


