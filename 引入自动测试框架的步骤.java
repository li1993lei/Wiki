在AndroidManifest.xml文件中配置一下信息:

    在manifest节点下:
    <instrumentation
        android:name="android.test.InstrumentationTestRunner"
        android:targetPackage="要测试的包名" />
 
    在application节点下配置下面信息:
    <uses-library android:name="android.test.runner" />



//测试时, 定义一个类继承AndroidTestCase

