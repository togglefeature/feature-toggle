# MVP Feature Toggles UI (current v0.3.0)

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

### Build Docker
```
export VUE_APP_BACKEND_URL=http://localhost:8080
docker build --build-arg VUE_APP_BACKEND_URL=$VUE_APP_BACKEND_URL -t feature-toggle-ui:0.3.0 .
```

### Run Docker
```
docker run --rm -d -p 8081:8080 feature-toggle-ui:0.3.0
```
