import {defineStore} from 'pinia'

export const viewTagsStore = defineStore({
	id: 'viewTags',
	state: () => ({
		viewTags: []
	}),
	getters: {},
	actions: {
		pushViewTags(route) {
			const target = this.viewTags.find((item) => item.fullPath === route.fullPath)
			const isName = route.name
			if (!target && isName) {
				this.viewTags.push(route)
			}
		},
		removeViewTags(route) {
			this.viewTags.forEach((item, index) => {
				if (item.fullPath === route.fullPath) {
					this.viewTags.splice(index, 1)
				}
			})
		},
		updateViewTags(route) {
			this.viewTags.forEach((item) => {
				if (item.fullPath == route.fullPath) {
					Object.assign(item, route)
				}
			})
		},
		updateViewTagsTitle(title = '') {
			const nowFullPath = location.hash.substring(1)
			this.viewTags.forEach((item) => {
				if (item.fullPath == nowFullPath) {
					item.meta.title = title
				}
			})
		},
		clearViewTags() {
			this.viewTags = []
		}
	}
})
