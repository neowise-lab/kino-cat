const kinoService = require('../data/service/KinoService')

const express = require('express')
const router = express.Router()

router.get('/kino/genre/:genre', async (req, res) => {
    const { genre } = req.params
    const kinoList = await kinoService.findByGenre(genre)
    res.json(kinoList)
})

router.get('/kino/:id', async (req, res) => {
    const { id } = req.params
    const kino = await kinoService.findById(id)
    res.json(kino)
})

router.get('/kino/search', async (req, res) => {
    const { criteria } = req.query
    const kinoList = await kinoService.findByCritera(criteria)
    res.json(kinoList)
})

router.post('/kino', async (req, res) => {
    const kino = await kinoService.addKino(req.body)
    res.json(kino)
})

module.exports = router