# FreePermission
一个利用apt实现的功能，只需要加一个注解，点击按钮之前能够获取到权限
写法类似这样


  @Permission(R.id.tv, READ_PHONE_PERMISSION)
  
    fun onClick() {
      val intent = Intent(this, TestActivity::class.java)
      startActivity(intent)
    }

目前只写了单个按钮点击跳转前获取权限





ps：其实如果是kotlin的话，用extension显然是更好的办法
