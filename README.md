# WELCOME STUDY

## 2015-07-17
1. 接口除了pub开头的接口，其他对外app接口都加前缀api,方便后台过滤。比如http://ip:port/api/user/info

 re:本条咱们自己在集成文档中注明就好。可以这样做
 
2. 采用http header 参数,web登陆后直接写cookie，子系统都需要添加filter，还需要redirect
 附带token？另外，h5是不是也属于写cookie,h5不写cookie就生成token也保证不了统一登录？只有ios活andriod登陆互斥就行。

re：h5按照app一样处理，返回token，h5的应用自己处理（h5有本地存储，所以不用cookie也可以存token）。 只有ios和andriod 互斥。

3. 所有接口参数首字母都是小写。

re：ok，要体现在咱们的集成文档中 

4. qq一键登录需要注册和审核，然后给我应用id和key。

re：咱们注册试试，需要信息找我要就好。

5. 基于邮箱的接口暂时没法做，没有页面，注册也没有填写邮箱.
re： ok

6. 系统错误参数补充（999:系统出现未知错误，9999：token失效）

re：不要把所有的在需求文档么有列出来的 都计入 999.特别常用的得增加系统错误参数。

7. 订单是否属于这期（账户扣款涉及到交易号，即订单号）。

re：订单不属于本期

8. 目前pdf有三大业务系统接口，本期完成的客户管理系统，是否都做？（因为支付和充值卡，分属于支付系统和充值卡系统）

9. 确认文档中的优惠劵相关接口，是账本类型的一种？（个人理解不用做）

re：本期不做。具体设计，二期再考虑，不作为账本，单独处理

10. 账户充值、账户扣帐、多账本管理接口对应pdf哪个接口？（最开始给的功能文档上，有定义账户充值、账户扣帐的接口，后来说接口以pdf为准，上面的充值变成了充值卡和现金，接口名和参数也都不一样。）

re：以pdf为准

11. 功能文档上的账户余额查询接口和pdf上的获取账户信息接口，是多个，还是一个？

re：是一个，账户信息包含余额了。

