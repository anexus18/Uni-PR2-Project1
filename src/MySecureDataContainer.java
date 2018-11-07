import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class MySecureDataContainer<E> implements SecureDataContainer<E> {
    //implementato con un'unica lista di user, password e data


    private class User{
        private String id;
        private String passw;
        private List<E> data;

        public User(String id, String passw, List<E> data){
            this.id = id;
            this.passw = passw;
            this.data = data;
        }


        public String getId() {
            return id;
        }

        public boolean samePassw(String passw) {
            return passw.equals(this.passw);
        }

        public List<E> getData() {
            return data;
        }

        public void addData(E newData) {
            data.add(newData);
        }
    }

    private ArrayList<User> container;

    public MySecureDataContainer(){
        container = new ArrayList<>();
    }

    @Override
    public void createUser(String Id, String passw) throws UserTakenException {
        Boolean found = false;
        for(User user : container)
            if(user.getId().equals(Id)) {
                found = true;
                break;
            }
        if(found)
            throw new UserTakenException();
        else{
            container.add(new User(Id, passw, new ArrayList<>()));
        }
    }

    @Override
    public int getSize(String Owner, String passw) throws IdNotFoundException, NotAuthorizedException {
        Boolean found = false;
        int size = -1;
        for(User user : container)
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)){
                    found = true;
                    size = user.getData().size();
                    break;
                }else
                    throw new NotAuthorizedException("Owner-Password mismatch");
        if(!found)
            throw new IdNotFoundException();
        return size;
    }

    @Override
    public boolean put(String Owner, String passw, E data) throws NotAuthorizedException, IdNotFoundException {
        Boolean found = false;
        for(User user : container)
            if(user.getId().equals(Owner))
                if(user.samePassw(passw)){
                    found = true;
                    user.addData(data);
                    break;
                }else
                    throw new NotAuthorizedException("Owner-Password mismatch");
        if(!found)
            throw new IdNotFoundException();
        return true;
    }

    @Override
    public E get(String Owner, String passw, E data) throws NotAuthorizedException, IdNotFoundException {
        for(User user: container){
            if(user.getId().equals(Owner))
                if(user.samePassw(passw))
                    return user.getData().get(0);
                else
                    throw new NotAuthorizedException("Owner-Password mismatch: cannot get data");
        }
        throw new IdNotFoundException();
    }

    @Override
    public E remove(String Owner, String passw, E data) {
        return null;
    }

    @Override
    public void copy(String Owner, String passw, E data) {

    }

    @Override
    public void share(String Owner, String passw, String Other, E data) {

    }

    @Override
    public Iterator<E> getIterator(String Owner, String passw) {
        return null;
    }


}