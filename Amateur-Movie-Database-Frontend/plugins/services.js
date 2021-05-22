import CelebrityService from '~/assets/service/CelebrityService.js'
import TitleService from '~/assets/service/TitleService.js'

export default (ctx, inject) => {
  const services = {
    title: new TitleService(ctx.$axios),
    celebrity: new CelebrityService(ctx.$axios)
  }

  inject('services', services)
}
