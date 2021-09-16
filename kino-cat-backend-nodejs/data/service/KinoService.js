class KinoService {

    constructor(kinoRepo) {
        this.kinoRepo = kinoRepo
    }

    async addKino(kino) {
        return await this.kinoRepo.insert(kino)
    }

    async findById(id) {
        return await this.kinoRepo.findById(id)
    }

    async findByGenre(genre) {
        return await this.kinoRepo.findByGenre(genre)
    }

    async findByCritera(criteria) {
        return await this.kinoRepo.findByCritera(criteria)
    }
}

module.exports = new KinoService()