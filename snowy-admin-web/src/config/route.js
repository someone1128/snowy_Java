
// 静态路由配置
const routes = {
	// 默认模块，仅限于后端未添加任何单页配置，用此路由
	module: [
		{
			id: '01',
			name: 'homeModule',
			path: '/homeModule',
			component: '',
			meta: {
				title: '默认',
				type: 'module',
				icon: 'bank-outlined'
			},
			children: []
		}
	],
	// 默认首页、个人中心
	menu: [
		{
			id: '001',
			name: 'index',
			path: '/index',
			component: 'index/index',
			meta: {
				title: '首页',
				type: 'menu',
				icon: 'bank-outlined',
				affix: true
			},
			children: []
		},
		{
			id: '002',
			name: 'userCenter',
			path: '/userCenter',
			component: 'sys/user/userCenter',
			meta: {
				title: '个人中心',
				type: 'menu',
				hidden: true
			},
			children: []
		}
	]
}

export default routes
