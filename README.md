# spring-gift-wishlist

---

## Step3

### 요구 사항
상품과 위시 리스트 보기에 페이지네이션을 구현한다.

대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.

### 리뷰 내용
**Step2**
> @Column(name = "name")  
> private Long value;  
> 🟢 바꾸실거면 변수명을 바꾸는게 더 낫지 않나요? 🤔
 
해당 객체인 ProductName 의 경우 변수명을 name 으로 하였지만,
Email 과 같은 몇몇 객체의 경우, 변수명을 email 로 설정하게되면  
`A field should not duplicate the name of its containing class`  
경고가 발생하게 됩니다. 때문에, 모든 변수명을 value 로 통일시키고, `@Column` 을 사용해주었습니다.
또한, Product가 아닌 다른 Table 에서 `name` column 을 만들게 될 경우나 클래스 이름을 수정하게 될 경우  
변수명과 실제 테이블 column 사이에서 일관성 문제가 발생할 수도 있다고 생각합니다.
그리고 value 로 통일하게 되면, Table 에서 Column명 수정이 훨씬 간편하다는 장점 또한 있습니다

> MyCrudRepository말고 JpaRepository 상속 받으면 똑같을 겁니다
 
JpaRepository를 그대로 상속 받게 되면, 사용 권한을 주고 싶지 않은 
`deleteInBatch`, `saveAll` 와 같은 함수들도 Service 에서 사용하게 될 수도 있다고 하여  
`@NoRepositoryBean` 을 적용시켜, 별도의 Repository를 만들어 보았습니다.

> hash Override가 조금 이상한데 다른 방식으로 정의해보시길 권합니다. 저러면 Equals나 HashMap 구조에서 문제가 생길 수 있어요!

```java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCount that = (ProductCount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
```
이 방식에 정확히 어떠한 문제가 있는 지 모르겠습니다.
GPT 를 통해 물어본 결과, 내부 value 값이 바뀐다면 문제가 생긴다고 하는데,
setter 를 사용하지 않는다면, 문제가 되지 않는 것 아닐까요?
또는, setter 를 사용해도 문제가 없도록 선언할 수 있는 방법이 있는 걸까요?

**step3**
> 너무 단순하게 얘기를 드린거 같아 말씀드립니다. name, value 같은 단순한 명칭은 java나 kotlin 그리고 db 상에서 그냥 썼을 때 두 가지 문제가 생깁니다.
> 하나는 데이터 자체를 알아보기 힘들 다는 문제입니다. 금융 상품이라고 가정을 했을 때 Bank -> ProductType -> Product -> Loan 이라는 계층을 가지는 DTO가 있다고 가정할게요. 우리가 Loan이라는 값을 가져올 거면 이렇게 될 겁니다.
> bank.getValue().getValue().getValue() 가시성이 엄청나게 떨어집니다. 저는 리뷰를 하면서 제일 중요하게 생각하는 점은 다른 사람이 내가 짜놓은 메소드 하나만 봐도 이해가 갈 수 있다. 입니다. 그래서 메소드 하나에서 볼 수 있는 칼럼명이나 메소드명을 한 번 Depth 더 찾아들어가야 확인 할 수 있도록 짜지 않길 권장 드린겁니다. 그래서 저는 productName 이런식으로 만드는 게 더 좋다고 생각합니다.
> 두 번째는 value 자체가 가지는 어휘의 특수성입니다. value는 다른 default 객체들에서 프로토 타입으로 많이 쓰이는 데, 문제는 그렇기 때문에 다른 메소드나 전체 환경에서 예상치 못하게 참조하여, 변수명 때문에 에러가 나는 경우도 가끔 존재합니다. 그래서 변수명은 특수하게 짓는게 좋습니다.

그렇다면, A field should not duplicate the name of its containing class 경고를 무시하고
Class 이름과 같은 이름의 변수명으로 관리하는 것이 좋다는 의미일까요?

> 코드를 짤 때 Why라는 이유를 생각해보는 게 좋습니다. 
> equals와 hashCode는 어떤 객체가 같은 값을 가지게 하는지 판별하게 하는 역할과 hash 함수에서 어떠한 방식으로 저장을 하게 하는지 결정하게 하는 함수입니다.  
> HashSet() 나 HashMap() 등이 저장되는 구조의 원리는 저 hashCode의 함수를 따라갑니다.  
> 내부에서 소수의 값을 곱해주죠. 이 부분은 나중에 기업 면접 등을 보실 때도 되게 중요한 부분입니다.  
> 오버라이드가 잘못 된 것이 아니라 hashCode 선언 방식을 한 번 고민해보시죠


클래스가 불변이고 해시코드를 계산하는 비용이 크다면, 매번 새로 계산하기 보다는 hashCode를 저장해두자. 
이 타입의 객체가 주로 해시의 키로 사용될 것 같다면 인스턴스가 만들어질 때 해시코드를 계산해야 한다. 
이를 위해 지연 초기화(lazy initialization) 전략을 사용할 수 있지만, 필드를 지연 초기화하기 위해서는 Thread-Safe 하도록 신경써야 한다.  
https://povia.tistory.com/87

이걸 말씀하시는 게 맞는 걸까요?

> CascadeType.REMOVE는 권장 드리지 않습니다 :) 실제로 쓰다가 큰일이 날 수도 있어요..  

넵. 수정하겠습니다

> Record 객체를 남용해서 쓰는 거 같습니다. 객체 변환의 주체는 Service Layer나 Dto, Entity 내에서 하는 게 더 좋습니다.  
> Response는 객체 하나만 보여주는게 좋습니다. ResultResponseDto라는 단어도 너무 범용적입니다.

'남용' 이라는 말은 Record 가 어울리지 않는 객체임에도 억지로 Record 로 선언을 하고 있다는 의미로 해석해도 될까요?  
어떤 경우에 Record 가 좋지 않은 지, 자세히 알고 싶습니다.  
Response 객체를 Service 에서 완전히 다 만들어서 주어야한다는 건 확실히 맹점이었던 것 같습니다. 수정하도록 하겠습니다  
'ResultResponseDto' 는 현재 모든 API 에서 반환값을 담당하는 객체이므로 '범용적' 인 것이 좋지 않을까요?  
'범용적' 이라는 단어를 기존 SpringBoot 에서 지원하는 다른 객체 이름과 헷갈릴 수 있다는 의미로 하신 말씀이라면,  
뒤에 'Dto' 라는 단어를 작성하였으므로, 그 부분에 대해서는 크게 신경쓰지 않아도 되지 않을까요?


### 구현 기능
이전 step2 에서 수정할 것들
- [x] Service test 작성하기
- [x] Cascade 수정하기

step2 에서 구현할 것들
- [x] 페이지네이션 API 구현하기
- [x] 페이지네이션 UI 구현하기






