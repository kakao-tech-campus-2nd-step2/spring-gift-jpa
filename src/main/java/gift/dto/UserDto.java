package gift.dto;

import gift.entity.User;

public class UserDto {

    public static class Request {
        private final Long id;
        private final String email;
        private final String password;

        public Request(Long id, String email, String password) {
            this.id = id;
            this.email = email;
            this.password = password;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Response {
        private final Long id;
        private final String email;
        private final String password;

        public Response(Long id, String email, String password) {
            this.id = id;
            this.email = email;
            this.password = password;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public static Response fromEntity(User user) {
            return new Response(user.getId(), user.getEmail(), user.getPassword());
        }
    }
}
