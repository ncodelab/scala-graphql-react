const todo = (state = {}, action) => {
  switch (action.type) {
    case 'ADD_TODO':
      return {
        id: action.id,
        text: action.text,
        editing: false,
        completed: false
      };
    case 'TOGGLE_TODO':
      if (state.id !== action.id) {
        return state
      }

      return Object.assign({}, state, {
        completed: !state.completed
      });

    case 'CHANGE_TODO':
      if(state.id !== action.id) {
        return state
      }

      return Object.assign({}, state, {
        text: action.text
      });

    case 'EDIT_TODO':
      if(state.id !== action.id) {
        return state
      }

      return Object.assign({}, state, {
        editing: !state.editing
      });


    case 'REMOVE_TODO':
      if(state.id !== action.id) {
        return state
      }
      return '';


    default:
      return state
  }
};

const todos = (state = [], action) => {
  switch (action.type) {
    case 'ADD_TODO':
      return [
        ...state,
        todo(undefined, action)
      ];
    case 'TOGGLE_TODO':
      return state.map(t =>
          todo(t, action)
      );
    case 'CHANGE_TODO':
      return state.map(t =>
          todo(t, action)
      );
    case 'EDIT_TODO':
      return state.map(t =>
          todo(t, action)
      );
    case 'REMOVE_TODO':
      return state.map(t =>
          todo(t, action)
      ).filter(t => t != '');
    default:
      return state
  }
};

export default todos
