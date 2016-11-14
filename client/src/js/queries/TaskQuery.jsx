const defaultStructure = `id
    name
    tasks{
      id
      title
      text
      status
    }`;

export const createTask = (folderId, text, title) => `query task {
  addTask(folderId: ${folderId}, title: "${title}", text: "${text}") {
      ${defaultStructure} 
    }
}`;

export const removeTask = (folderId, taskId) => `query task {
  removeTask(folderId: ${folderId}, taskId: ${taskId}) {
    ${defaultStructure} 
  }
}`;

export const setTaskText = (folderId, taskId, text) => `query task {
  setTaskText(folderId: ${folderId}, taskId: ${taskId}, text: "${text}") {
    ${defaultStructure} 
  }
}`;

export const setStatus = (folderId, taskId, status) => `query task {
  setStatus(folderId: ${folderId}, taskId: ${taskId}, status: ${status}) {
    ${defaultStructure} 
  }
}`;
