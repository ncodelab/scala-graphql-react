import {Event} from "../actions/index.jsx"

const visibilityFilter = (state = 'SHOW_ALL', action) => {

  switch (action.type) {
    case Event.SET_VISIBILITY_FILTER.valueOf():
      return action.state;

    default:
      return state
  }
};

export default visibilityFilter
