import {combineReducers} from 'redux'
import todos from './todos.jsx'
import visibilityFilter from './visibilityFilter.jsx'
import {createStore, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';


const FolderQuery = require("../queries/FolderQuery.jsx");


const todoApp = combineReducers({
  todos,
  visibilityFilter
});

export const store = createStore(
    todoApp,
    applyMiddleware(thunk));
