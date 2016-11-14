import Relay from 'react-relay';

export default {
  getFolder: (id) => Relay.QL`query {
  getFolder(folderId: "${id}") {
    id
    name
    tasks{
      id
      title
      text
      status
    }
  }
}`
};
