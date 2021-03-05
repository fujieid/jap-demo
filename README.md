<p align="center">
	<img src="https://images.gitee.com/uploads/images/2021/0218/094114_99925b8b_784199.png" width="200">
</p>
<p align="center">
	<strong>Just auth into any app</strong>
</p>
<p align="center">
	<a target="_blank" href="https://search.maven.org/search?q=jap">
	  <img src="https://img.shields.io/badge/Maven%20Central-1.0.0-blue" ></img>
	</a>
	<a target="_blank" href="https://gitee.com/yadong.zhang/JustAuth/blob/master/LICENSE">
	  <img src="https://img.shields.io/badge/license-LGPL%203.0-red" ></img>
	</a>
	<a target="_blank" href="https://apidoc.gitee.com/fujieid/jap" title="API文档">
	  <img src="https://img.shields.io/badge/Api%20Docs-1.0.0-orange" ></img>
	</a>
	<a target="_blank" href="https://justauth.plus" title="开发文档">
	  <img src="https://img.shields.io/badge/Docs-latest-blueviolet.svg" ></img>
	</a>
  <a target="_blank" href="https://codecov.io/gh/fujieid/jap" title="开发codecov档">
	  <img src="https://codecov.io/gh/fujieid/jap/branch/master/graph/badge.svg?token=WmfmgwxtnJ" ></img>
	</a>
  <a target="_blank" href="https://travis-ci.com/fujieid/jap" title="开发codecov档">
	  <img src="https://travis-ci.com/fujieid/jap.svg?branch=master&status=passed" ></img>
	</a>
	<a target="_blank" href="https://gitter.im/fujieid/JAP?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge">
	  <img src="https://badges.gitter.im/fujieid/JAP.svg" ></img>
	</a>
</p>
<p align="center">
  <a target="_blank" href='https://gitee.com/fujieid/jap/stargazers'>
    <img src="https://gitee.com/fujieid/jap/badge/star.svg?theme=white" alt='star'></img>
  </a>
  <a target="_blank" href='https://github.com/fujieid/jap/stargazers'>
    <img src="https://img.shields.io/github/stars/fujieid/jap?style=social" alt='star'></img>
  </a>
</p>
<p align="center">
	<strong>开源地址：</strong> <a target="_blank" href='https://gitee.com/fujieid/jap'>Gitee</a> | <a target="_blank" href='https://github.com/fujieid/jap'>Github</a>
</p>
<p align="center">
	<strong>官方网站（Wiki）：</strong> <a target="_blank" href='https://justauth.plus'>https://justauth.plus</a>
</p>


## 可选 Demo

- 普通示例项目：[jap-demo](https://gitee.com/fujieid/jap-demo)
- 前后端分离项目示例：[jap-demo-vue](https://gitee.com/fujieid/jap-demo-vue)


## Demo 使用方式

本 Demo 项目默认开启了 SSO 功能，所以需要提前配置 Hosts

修改本地 hosts，加入以下配置

```
127.0.0.1 sso.jap.com
127.0.0.1 sso1.jap.com
127.0.0.1 sso2.jap.com
127.0.0.1 sso3.jap.com
```

启动项目后，使用以上配置的任意域名 + 端口号访问，如：[http://sso.jap.com:8443](http://sso.jap.com:8443)

更多使用帮助，请参考：

- [使用 jap-simple](https://justauth.plus/quickstart/jap-simple.html) ：实现账号密码登录
- [使用 jap-social](https://justauth.plus/quickstart/jap-social.html) ：实现第三方账号登录
- [使用 jap-oauth2](https://justauth.plus/quickstart/jap-oauth2.html) ：实现 OAuth 协议登录
- [使用 jap-oidc](https://justauth.plus/quickstart/jap-oidc.html) ：实现 OIDC 协议登录
- [使用 jap-sso](https://justauth.plus/quickstart/jap-sso.html) ： 实现单点登录
- [使用 jap-mfa](https://justauth.plus/quickstart/jap-mfa.html) ： 实现多因素认证

## 项目说明

`JapApiController`为模拟的受保护的资源服务，登录前访问该类下的接口，会跳转到首页并提示“未登录”，登录后访问该类下的接口，可以正常访问
