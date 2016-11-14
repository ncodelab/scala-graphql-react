import React, {PropTypes} from 'react'
import Todo from './Todo.jsx'

const TodoList = ({todos, onTodoClick, onTodoEdit, onTodoChange, onTodoRemove}) => (
    <section className="main">
      <input className="toggle-all" type="checkbox"/>
        <label htmlFor="toggle-all">Mark all as complete</label>
        <ul className="todo-list">
          {todos.map(todo =>
              <Todo
                  key={todo.id}
                  {...todo}
                  onTodoEdit={() => onTodoEdit(todo.id)}
                  onTodoComplete={() => onTodoClick(todo.id)}
                  onTodoChange={(evt) => onTodoChange(todo.id, evt.target.value)}
                  onTodoRemove={() => onTodoRemove(todo.id)}
              />
          )}
        </ul>
    </section>
);

TodoList.propTypes = {
  todos: PropTypes.arrayOf(PropTypes.shape({
  id: PropTypes.number.isRequired,
  completed: PropTypes.bool.isRequired,
  text: PropTypes.string.isRequired
}).isRequired).isRequired,
  onTodoClick: PropTypes.func.isRequired,
  onTodoRemove: PropTypes.func.isRequired,
  onTodoChange: PropTypes.func.isRequired
};

export default TodoList
