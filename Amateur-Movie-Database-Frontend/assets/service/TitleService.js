import Title from '~/assets/data/Title.class.js'
import TitleDisplay from '~/assets/data/TitleDisplay.class.js'

export default class TitleApi {
  constructor ($axios) {
    this.axios = $axios
  }

  findByName (searchQuery) {
    return this.axios({
      method: 'get',
      url: 'title?title=' + searchQuery
    }).then((response) => {
      const titles = []
      response.data.forEach((title) => {
        titles.push(new Title(title.id, title.name, title.poster, title.year))
      })
      return titles
    })
  }

  findById (id) {
    return this.axios({
      method: 'get',
      url: 'title/' + id
    }).then((response) => {
      return new TitleDisplay(response.data.id, response.data.name, response.data.poster, response.data.plot,
        response.data.year, response.data.votes, response.data.duration, response.data.rating,
        response.data.genres, response.data.directors, response.data.writers, response.data.stars)
    })
  }
}
