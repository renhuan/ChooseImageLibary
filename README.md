# ChooseImageLibary(已废弃)
图片选择库

![例如](https://github.com/renhuan2015/ChooseImageLibary/blob/master/app/img/a.jpg)


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan:ChooseImageLibary:2.0'
	}

用到的第三方库有

    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'
    api 'com.github.bumptech.glide:glide:4.1.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
    api 'me.iwf.photopicker:PhotoPicker:0.9.12@aar'

用法：
第一步：在Activity的xml中布局

    <com.example.rhchooseimagelibary.UploadImage
        android:id="@+id/upImage"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

第二步：在Activity中设置适配器

        UploadImage upImage = findViewById(R.id.upImage);
        upImage.setAdapter(4, new ArrayList<>(), this);

第三步：在Activity中设置监听

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> list = upImage.onActivityResult(requestCode, resultCode, data);
        System.out.println("----" + list.toString());
    }

    @Override
    public void delete(List<String> list) {
        System.out.println("----" + list.toString());
    }
