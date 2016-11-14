require('es5-shim');
require('es6-shim');

import React from 'react'
import {bootstrapData} from "./actions/async.jsx";
import {render} from 'react-dom'
import {Provider} from 'react-redux'
import {createStore} from 'redux'
import {store} from './reducers/index.jsx'
import App from './components/App.jsx'

import '../css/style.scss';

bootstrapData();
render(
    <Provider store={store}>
      <App />
    </Provider>,
    document.getElementById('application')
);
