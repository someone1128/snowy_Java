import {defineStore} from 'pinia'

export const keepAliveStore = defineStore({
	id: 'keepAlive',
	state: () => ({
		keepLiveRoute: [],
		routeKey: null,
		routeShow: true
	}),
	getters: {},
	actions: {
		pushKeepLive(component) {
			if (!this.keepLiveRoute.includes(component)) {
				this.keepLiveRoute.push(component)
			}
		},
		removeKeepLive(component) {
			const index = this.keepLiveRoute.indexOf(component)
			if (index !== -1) {
				this.keepLiveRoute.splice(index, 1)
			}
		},
		clearKeepLive() {
			this.keepLiveRoute = []
		},
		setRouteKey(key) {
			this.routeKey = key
		},
		setRouteShow(key) {
			this.routeShow = key
		},
		setRouteKeyAction(key) {
			this.setRouteKey(key)
		}
	}
})
