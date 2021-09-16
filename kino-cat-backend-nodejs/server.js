const path = require('path')

const express = require('express')
const session = require('express-session')

const bodyParser = require('body-parser')

const connect = require('connect')
const cookieParser = require('cookie-parser')

const handlebars = require('express-handlebars')

const app = express()
app.use(bodyParser.urlencoded({ extended: false }))

// ------------- handlebars ---------------------- //
app.set('view engine', 'hbs')
app.engine('hbs', handlebars({
    layoutDir: __dirname + 'views/layouts',
    extname: 'hbs'
}))
app.use(express.static(path.join(__dirname, 'public')))

// ------------- sessions ---------------------- //
app.use(cookieParser());
app.use(session({ 
        secret: 'morfology-secret-key-9997',
        proxy: true,
        resave: true,
        saveUninitialized: true 
    }));

// ------------- routes ---------------------- //

const MainRoute = require('./routes/MainRoute')
const AuthRoute = require('./routes/KinoRoute')
const SentencesRoute = require('./routes/SentencesRoute')
const TestRoute = require('./routes/TestRoute')

app.use('/', MainRoute)
app.use('/', AuthRoute)
app.use('/', SentencesRoute)
app.use('/', TestRoute)

app.listen(3000, () => {
    console.log('server is running on http://localhost:3000')
})