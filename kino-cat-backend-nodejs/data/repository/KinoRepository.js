const db = require('../database')

const KINO_INSERT = "INSERT INTO Kino(time, name, description, country, genre, year, color, imageUrl) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
const FIND_BY_ID = "SELECT * FROM Kino WHERE id = ?;"
const FIND_BY_TIME = "SELECT * FROM Kino WHERE time = ?;"
const FIND_ALL = "SELECT * FROM Kino;"
const FIND_BY_GENRE = "SELECT * FROM Kino WHERE genre = ?;"
const FIND_BY_CRITERIA = "SELECT * FROM Kino WHERE genre = ?;"
const REMOVE = "DELETE FROM Kino WHERE id = ?;"

class KinoRepository {

    async insert(kino) {
        const time = Date()
        await db.run(KINO_INSERT,
            [   time,
                kino.name,
                kino.description,
                kino.country,
                kino.genre,
                kino.year,
                kino.color,
                kino.imageUrl
            ]
        )
        return await this.findByTime(time)
    }

    async findByTime(time) {
        return await db.get(FIND_BY_TIME, [time])
    }

    async findById(id) {
        return await db.get(FIND_BY_ID, [id])
    }

    async findByGenre(genre) {
        return await db.all(FIND_BY_GENRE, [genre])
    }

    async findByCriteria(criteria) {
        return await db.all(FIND_BY_CRITERIA, [criteria])
    }

    async findAll() {
        return await db.all(FIND_ALL)
    }

    async remove(id) {
        await db.run(REMOVE, [id])
    } 
}

module.exports = new KinoRepository()