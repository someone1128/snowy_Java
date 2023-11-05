<template>
	<a-row :gutter="10">
		<a-col :span="16">
			<UserInfo class="mb-2" />
			<shortcut class="mb-2" />
			<a-row :gutter="10">
				<a-col :span="12">
					<visLog class="mb-2" />
				</a-col>
				<a-col :span="12">
					<opLog class="mb-2" />
				</a-col>
			</a-row>
		</a-col>
		<a-col :span="8">
			<schedule class="mb-2" />
			<miniMessage class="mb-2" />
		</a-col>
	</a-row>
</template>

<script setup name="indexHome">
	import shortcutCard from '@/components/ShortcutCard/index.vue'
	import userInfo from './components/userInfo.vue'
	import shortcut from './components/shortcut.vue'
	import schedule from './components/schedule.vue'
	import miniMessage from './components/miniMessage.vue'
	import visLog from './components/visLog.vue'
	import opLog from './components/opLog.vue'
</script>
