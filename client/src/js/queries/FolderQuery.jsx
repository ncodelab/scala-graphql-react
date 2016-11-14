export const getFolder = (id) => `query folder {
  getFolder(folderId: ${id}) {
    id
    name
    tasks{
      id
      title
      text
      status
    }
  }
}`;
