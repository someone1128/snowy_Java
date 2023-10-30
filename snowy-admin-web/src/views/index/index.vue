<template>
	<a-row :gutter="10">
		<a-col :span="16">
			<userInfo class="mb-2" />
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

<script name="indexHome" setup></script>
