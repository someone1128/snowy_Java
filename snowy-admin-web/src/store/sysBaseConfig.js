import configApi from '@/api/dev/configApi'
import {message} from 'ant-design-vue'

const formData = ref({
	SNOWY_SYS_LOGO: '',
	SNOWY_SYS_BACK_IMAGE: '',
	SNOWY_SYS_NAME: '',
	SNOWY_SYS_VERSION: '',
	SNOWY_SYS_COPYRIGHT: '',
	SNOWY_SYS_COPYRIGHT_URL: '',
	SNOWY_SYS_DEFAULT_FILE_ENGINE: 'LOCAL',
	SNOWY_SYS_DEFAULT_CAPTCHA_OPEN: false,
	SNOWY_SYS_DEFAULT_PASSWORD: ''
})

const param = {
	category: 'SYS_BASE'
}

const getSysBaseConfig = () => {
	configApi.configList(param).then((data) => {
		if (data) {
			data.forEach((item) => {
				formData.value[item.configKey] = item.configValue ? '' : item.configValue
			})
		} else {
			message.warning('表单项不存在，请初始化数据库')
		}
	})
}
