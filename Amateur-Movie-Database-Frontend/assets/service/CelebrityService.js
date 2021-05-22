import CelebrityDisplay from '~/assets/data/CelebrityDisplay.class.js'
import Celebrity from '~/assets/data/Celebrity.class.js'

export default class CelebrityService {
  constructor ($axios) {
    this.axios = $axios
  }

  findByName (searchQuery) {
    return this.axios({
      method: 'get',
      url: 'name?name=' + searchQuery
    }).then((response) => {
      const celebrities = []
      response.data.forEach((celebrity) => {
        celebrities.push(new Celebrity(celebrity.id, celebrity.fullName, celebrity.poster, celebrity.birthYear))
      })
      return celebrities
    })
  }

  findById (id) {
    return this.axios({
      method: 'get',
      url: 'name/' + id
    }).then((response) => {
      return new CelebrityDisplay(response.data.id, response.data.fullName, response.data.description,
        response.data.poster, response.data.birthYear, response.data.deathYear, response.data.professions,
        response.data.knownFor)
    })
  }
}
