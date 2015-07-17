WELCOME STUDY
#1.接口除了pub开头的接口，其他对外app接口都加前缀api,方便后台过滤。比如http://ip:port/api/user/info
#2.采用http header 参数,web登陆后直接写cookie，子系统都需要添加filter，还需要redirect 附带token？另外，h5是不是也属于写cookie,h5不写cookie就生成token也保证不了统一登录？只有ios活andriod登陆互斥就行。
#3.所有接口参数首字母都是小写。
#4.qq一键登录需要注册和审核，然后给我应用id和key。
#5.基于邮箱的接口暂时没法做，没有页面，注册也没有填写邮箱.
#6.系统错误参数补充（999:系统出现未知错误，9999：token失效）


