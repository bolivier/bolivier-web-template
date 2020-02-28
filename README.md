# bolivier Web

A Leiningen template for me to use for web projects

#### It includes

* Aleph for http
* Reitit for routing
* graphql (via Lacinia)
* Postgresql support w/ next.jdbc
* HoneySQL
* Mount
* Shadow-CLJS
* Reagent/React

#### Potentially include:

* Re-Frame

## Usage

To start the server, launch a repl, you should find yourself in the `user` ns.

From there run

```
> (start)
```

Then, to build the frontend, go to a terminal and run:

```
shadow-cljs watch frontend
```

## License

Copyright © 2020 Brandon Olivier

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
