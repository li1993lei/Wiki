

//代码里

setContentView(R.layout.activity_home);



//清单文件
<application android:icon="@drawable/icon"   
        android:label="@string/app_name"   
        android:theme="@android:style/Theme.NoTitleBar">  



//3在style.xml文件里定义

[html] view plain copy
<?xml version="1.0" encoding="UTF-8" ?>  
<resources>  
    <style name="notitle">  
        <item name="android:windowNoTitle">true</item>  
    </style>   
</resources>  
然后面manifest.xml中引用就可以了，这种方法稍麻烦了些。
[html] view plain copy

<application android:icon="@drawable/icon"   
        android:label="@string/app_name"   
        android:theme="@style/notitle">  
