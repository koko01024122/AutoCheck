# Android表单自动校验库 AutoCheck

AutoCheck is a form check lib for Android. 

在日常工作中面我们总是会需要写一些无聊的表单页面，以及对这些表单界面进行一些业务逻辑的处理，比如判空，正则规则校验等，我个人是很懒的，这些代码写几遍难免烦躁，于是乎就尝试在网上找一些Android表单验证库，看了以后感觉还是好麻烦（没错，就是这么懒）。

不是有句话说“You can you up,no can no BB”嘛，本着这种精神，我鼓捣了AutoCheck这个lib。

先讲下工作原理，我自定义了两个ViewGroup以及两个表单控件，
ViewGroup分别继承于LinearLayout和RelativeLayout这两个最常用的ViewGroup,表单控件继承与EditText和TextView,这四个View中都实现了一个名为Check返回值为一个boolen类型的方法，用于判断其表单内容是否通过，在调用viewgroup的check方法时，viewgroup会循环调用子view的check方法，验证不通过的，则返回false，且可以通过getFinalHint()获取到最终的提示消息，下面我们来看这样一个常见的表单。


```xml
<nospy.com.autocheck.views.ACLinearLayout
    android:id="@+id/check_root"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="nospy.com.autocheckdemo.MainActivity">
    <nospy.com.autocheck.views.ACLinearLayout
        android:id="@+id/test1"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3.5"
            android:gravity="center"
            android:text="姓名"
            android:textSize="15sp"/>
        <nospy.com.autocheck.views.ACEditText
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6.5"
            android:background="#fff"
            android:maxLines="1"
            android:inputType="text"
            android:hint="请输入姓名"
            app:ACEditErrorHint="姓名格式错误"
            app:ACEditNullHint="姓名不能为空"
            app:ACEditNullable="false"
            app:ACEditRule="@string/name"/>
    </nospy.com.autocheck.views.ACLinearLayout>
    <nospy.com.autocheck.views.ACLinearLayout

        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3.5"
            android:gravity="center"
            android:text="手机号"
            android:textSize="15sp"/>
        <nospy.com.autocheck.views.ACEditText
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6.5"
            android:background="#fff"
            android:hint="请输入手机号"
            android:inputType="number"
            android:maxLines="1"
            android:maxLength="11"
            app:ACEditErrorHint="手机号格式错误"
            app:ACEditNullHint="手机号不能为空"
            app:ACEditNullable="false"
            app:ACEditRule="@string/mobel_phone"/>
    </nospy.com.autocheck.views.ACLinearLayout>
    <nospy.com.autocheck.views.ACLinearLayout

        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3.5"
            android:gravity="center"
            android:text="身份证号"
            android:textSize="15sp"/>
        <nospy.com.autocheck.views.ACEditText
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6.5"
            android:background="#fff"
            android:maxLines="1"
            android:inputType="number"
            android:maxLength="18"
            android:hint="请输入身份证号"
            app:ACEditErrorHint="身份证号格式错误"
            app:ACEditNullHint="身份证号不能为空"
            app:ACEditNullable="false"
            app:ACEditRule="@string/id_card"/>
    </nospy.com.autocheck.views.ACLinearLayout>
    <nospy.com.autocheck.views.ACLinearLayout

        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal">
        <TextView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3.5"
            android:gravity="center"
            android:text="邮箱"
            android:textSize="15sp"/>
        <nospy.com.autocheck.views.ACEditText
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="6.5"
            android:background="#fff"
            android:hint="请输入邮箱"
            android:inputType="textEmailAddress"
            android:maxLines="1"
            app:ACEditErrorHint="邮箱格式错误"
            app:ACEditNullHint="邮箱不能为空"
            app:ACEditNullable="false"
            app:ACEditRule="@string/email"/>
    </nospy.com.autocheck.views.ACLinearLayout>
    <Button
        android:id="@+id/sub"
        android:text="提交"
        android:layout_marginTop="10dp"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"/>
</nospy.com.autocheck.views.ACLinearLayout>

```
![enter description here][1]


对这个表单进行校验需要多少代码呢？
加上findview的代码，只需要10行

```java
  private ACLinearLayout mCheckRoot;
    private Button mSubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//
        mCheckRoot=(ACLinearLayout) findViewById(R.id.check_root);
        //需要校验的表单的根节点
        mSubButton=(Button)findViewById(R.id.sub);
        //用于触发校验的按钮
        mCheckRoot.setOutRing(true);
        //设置根节点为最外层
        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckRoot.check()){
                  //校验通过
                }else {
                    //校验未通过
                    String hint=mCheckRoot.getFinalHint();//获取最终的提示信息
                }

            }
        });
    }

```
以上是最基本的用法，如果单纯的Textview 和EditText不能够满足产品需求，你也可以通过实现 CheckViewGroupImpl、  CheckViewRuleImpl、HintViewImpl 这三个接口来自定义你的表单控件，下面介绍一下这三个接口中的属性
说明： 以下的Object类型的对象都是View装箱后的。。

CheckViewGroupImpl
| 属性名    | 值类型 | 说明                                                           |
| --------- | ------ | -------------------------------------------------------------- |
| isOuting  | boolen | 用于判断当前ViewGroup是否为(相对)最外层ViewGroup               |
| finalHint | String | 最终返回的提示信息                                             |
| autoHint  | boolen | 是否自动显示提示信息                                           |
| hintView  | Object | 用于显示提示信息的View,目前只为HintViewImpl设计了showError方法 |
|check()|boolen|规则校验的代码写在这里
|getFianlHint()|String|向外提供最终的提示信息
|setIsOutRing()|boolen|设置是否为最外圈
|getHintView()|Object|如果其不为最外圈提示view，获取其内部的提示控件
|setHintView()|Object|为其设置提示控件。

 CheckViewRuleImpl\<T>
| 属性名    | 值类型 | 说明                                                           |
| --------- | ------ | -------------------------------------------------------------- |
|relyMode|int| 该View的依赖模式，当该View的依赖列表不为空时，应先对依赖的View进行check，当值为 Modes.RELY_ON_MODE_WITH 时，需要全部依赖都满足才可进行自身的check,当值为Modes.RELY_ON_MODE_OR 时，只要有一个满足即可通过进行自身校验。
|finalHint|String|最终的提示信息
|relyOnList|ArrayList\<Object>|依赖列表
|tag|String|用于其他作用的标签
|rule|String|正则校验规则
|nullable|boolen|是否允许为空
|notBeList|ArrayList\<Stringt>|不允许的值列表
|nullHint|String|nullable为false时内容为空的提示。
|errorHint|String|不符合规则时的错误信息提示
|一干set方法|T|此处使用了泛型，是为了方便使用时的链式调用，set后返回的是当前的对象
|getFinalHint()|String|获取最终的提示信息
|checkSelf()|boolen|依赖项检查后的自检
|check()|boolen|启动校验，你也可以把checkSelf()中的代码放进来，但是看上去可能会比较臃肿
|setHintView()|Object|设置私有的提示消息控件

HintViewImpl

|属性名|值类型|说明
| --------- | ------ | -------------------------------------------------------------- |
|showError()|String|错误信息展示方法
|isPublic()|boolen|获取是否是公共的消息提示控件
|setPub|boolen|设置其可访问性 true为公共，false为私有
|pub|boolen|默认为公共

吃饭去，剩下的我们回头说。
  [1]: https://ooo.0o0.ooo/2017/09/26/59ca147dcb5fe.jpg
