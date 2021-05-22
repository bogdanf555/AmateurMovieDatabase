export default class Celebrity {
  constructor (id, name, poster, birth) {
    this.id = id
    this.title = name
    if (poster != null) {
      this.poster = poster
    } else {
      this.poster = '/blank_person.jpg'
    }
    this.year = birth
  }
}
