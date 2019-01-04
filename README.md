# ChooseImageLibary
图片选择库

![例如](https://github.com/renhuan2015/ChooseImageLibary/blob/master/app/img/a.jpg)


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.renhuan2015:ChooseImageLibary:1.0'
	}

用到的第三方库有

    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'
    api 'com.github.bumptech.glide:glide:4.1.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
    api 'me.iwf.photopicker:PhotoPicker:0.9.12@aar'
