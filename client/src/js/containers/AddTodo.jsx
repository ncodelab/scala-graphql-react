import React from 'react'
import { connect } from 'react-redux'
import { addTodo } from '../actions/index.jsx'

let AddTodo = ({ dispatch }) => {
  let input;

  return (
        <form onSubmit={e => {
          e.preventDefault();
          if (!input.value.trim()) {
            return
          }
          dispatch(addTodo(input.value));
          input.value = ''
        }}>
          <input className="new-todo" placeholder="What needs to be done?" ref={node => {
            input = node
          }} />
        </form>
  )
};
AddTodo = connect()(AddTodo);

export default AddTodo
