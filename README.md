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

    tv = findViewById(R.id.tv)
    tv?.permissionClickListener(this, READ_PHONE_PERMISSION) {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }

permissionClickListener的实现如下

    fun View.permissionClickListener(act: Activity, permission: Int, permissionClick: (Int) -> Unit) {
        val clickListener = View.OnClickListener {
            PermissionUtils.checkHavePermission(act, permission, object : PermissionUtils.PermissionListener {
                override fun onSuccess() {
                    super.onSuccess()
                    permissionClick(permission)
                }

                override fun onFail() {
                    super.onFail()
                }
            })
       }
       setOnClickListener(clickListener)
    }

获取权限用的是https://github.com/yanzhenjie/AndPermission
