import {Status} from "./Status.jsx";

export default class Todo {
  constructor(id, title, text, status) {
    this.id = id;
    this.title = title;
    this.text = text;
    this.status = status;
    this.editing = false;
  }

  setEditing(editing) {
    this.editing = editing;
  }

  setText(text) {
    this.text = text;
  }

  isCompleted() {
    return this.status === Status.FINISH
  }
}
