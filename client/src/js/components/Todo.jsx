import React, {PropTypes} from 'react'
let classNames = require('classnames');

const Todo = ({onTodoRemove, onTodoChange, onTodoEdit, onTodoComplete, editing, completed, text}) => {
  let liClasses = classNames({
    'completed': completed,
    'editing': editing
  });

  return (
      <li className={liClasses}>
        <div className="view">
          <input className="toggle" type="checkbox" onClick={onTodoComplete}
                 checked={completed}/>
          <label onClick={onTodoEdit}>{text}</label>
          <button className="destroy" onClick={onTodoRemove}></button>
        </div>
        <input className="edit" value={text} onChange={onTodoChange}
               onKeyPress={(evt) => {
                 if (evt.nativeEvent.keyCode == 13) {
                   onTodoEdit();
                 }
               }}/>
      </li>
  )
};

Todo.propTypes = {
  onTodoRemove: PropTypes.func.isRequired,
  onTodoChange: PropTypes.func.isRequired,
  onTodoEdit: PropTypes.func.isRequired,
  onTodoComplete: PropTypes.func.isRequired,
  editing: PropTypes.bool.isRequired,
  completed: PropTypes.bool.isRequired,
  text: PropTypes.string.isRequired
};

export default Todo
