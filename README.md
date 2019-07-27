# Employees-Api-Clojure

This library is a port of [Employees-Api-Coconut-Lang](httpsEmployees-Api-Coconut-Lang) to [Clojure](https://clojure.org/) and is devided into two parts:

1. A backend built in Clojure and uses [httpkit](https://www.http-kit.org/) and [ring](https://github.com/ring-clojure/ring) for http stuff and [toucan](https://github.com/metabase/toucan) for orm.

2. A frontend built in Clojurescript, running reagent (for React) and cljs-http.client.

## Getting started

- `brew install clojure`
- `brew install leiningen`

### Backend
- `cd backend`
- `docker-compose up`
- `lein run`
- `curl http://127.0.0.1:8080/employees`

### Frontend

- `cd frontend`
- `lein figwheel`

## License

This project is released under the [MIT License](http://www.opensource.org/licenses/MIT).
