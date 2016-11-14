import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import todoApp from './reducers/index.jsx'
import App from './components/App.jsx'

import '../css/style.scss';

let store = createStore(todoApp);

render(
    <Provider store={store}>
      <App />
    </Provider>,
    document.getElementById('application')
);
