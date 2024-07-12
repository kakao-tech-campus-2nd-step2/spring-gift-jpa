# spring-gift-jpa

# 진행 방식
- 미션은 과제 진행 요구 사항, 기능 요구 사항, 프로그래밍 요구 사항 세 가지로 구성되어 있다.
- 세 개의 요구 사항을 만족하기 위해 노력한다. 특히 기능을 구현하기 전에 기능 목록을 만들고, 기능 단위로 커밋 하는 방식으로 진행한다.
- 기능 요구 사항에 기재되지 않은 내용은 스스로 판단하여 구현한다.

# 과제 진행 요구 사항
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.

- 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
- 페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.

# 힌트
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.

- 이를 직접 구현할 수도 있지만, 스프링 데이터는 Pageable이라는 객체를 제공하여 쉽게 구현할 수 있다. 또한 List, Slice, Page 등 다양한 반환 타입을 제공한다.
