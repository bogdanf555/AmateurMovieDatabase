export default class Title {
  constructor (id, title, poster, year) {
    this.id = id
    this.title = title
    if (poster != null) {
      this.poster = poster
    } else {
      this.poster = '/film.jpg'
    }
    this.year = year
  }
}
