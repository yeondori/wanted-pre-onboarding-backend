# wanted-pre-onboarding-backend

🚀[프리온보딩 백엔드 인턴십 선발과제](https://bow-hair-db3.notion.site/1850bca26fda4e0ca1410df270c03409) 수행 내용
자세한 수행 과정 기록은 <https://yeondori.github.io/> 에서 볼 수 있습니다.

## 👆🏻프로젝트 요구사항

아래의 서비스 개요 및 요구사항을 만족하는 API 서버를 구현한다.

### 서비스 개요
- 본 서비스는 기업의 채용을 위한 웹 서비스이다.
- 회사는 채용공고를 생성하고, 이에 사용자는 지원한다.

### 서비스 요구사항

1. 채용공고 등록 
2. 채용공고 수정 (회사 id 이외 모두 수정 가능)
3. 채용공고 삭제
4. 채용공고 목록 가져오기

   ➡️ 사용자는 채용공고 목록을 아래와 같이 확인할 수 있다.
   > Example)
       # 데이터 예시이며, 필드명은 임의로 설정가능합니다.
       {
       "회사_id":회사_id,
       "채용포지션":"백엔드 주니어 개발자",
       "채용보상금":1000000,
       "채용내용":"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
       "사용기술":"Python"
       }           
        
      ➡️ 채용공고 검색 기능 구현(선택사항 및 가산점요소)


5. 채용 상세 페이지

   ➡️ 사용자는 채용상세 페이지를 확인할 수 있다.

   ➡️“채용내용”이 추가적으로 담겨있음

   ➡️해당 회사가 올린 다른 채용공고가 추가적으로 포함 **(선택사항 및 가산점요소).**

6. 사용자는 채용공고에 지원(선택사항 및 가산점요소)

   ➡️ 사용자는 채용공고 지원 (가점 요소이며, 필수 구현 요소가 아님) 

   ➡️사용자는 1회만 지원 가능

### 개발 시 참조사항

- 요구사항(의도)을 만족시킨다면 다른 형태의 요청 및 리스폰스를 사용하여도 좋다.
- 필요한 모델: 회사, 사용자, 채용공고, 지원내역(선택사항)
  (이외 추가 모델정의는 자유)
- 필드명은 임의로 생성 가능.
- 회사, 사용자 등록 절차는 생략.
  (DB에 임의로 생성하여 진행)
- 로그인 등 사용자 인증절차(토큰 등)는 생략.
- Frontend 요소(html, css, js 등)는 개발 범위에 제외.
  (구현시 불이익은 없지만, 평가에 이점 또한 없습니다.)
- 명시되지 않은 조건 또한 자유롭게 개발 가능.

## ⚙️프로젝트 구축 ![img](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) ![img](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

- Project: Gradel
- Language: Java
- Spring Boot: 3.1.3 
- Java: 17
- Dependencies: Lombok, Spring Web, Thymeleaf, Spring Data JPA, H2 Database ... 

## 📄데이터 모델링 및 계층 구조

### 데이터 모델링
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/e0e2220b-ac70-41c3-98e6-548186654279)

### Web Appication 계층 구조
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/1c1f068b-31ec-4c3b-a375-1d98804411b4)

### 초기 데이터
data.sql 참고
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/c67b38c2-1b0a-464c-88bb-092a07d174ed)

## 📑API 설계
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/17aa82f6-cf78-4f56-8728-af9da73453a3)

## 📑Web Application 구성

![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/0659af8b-84af-41a9-8751-f6aadd180e25)

### 메인 페이지
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/3354617a-49c5-443b-bc6f-e3cf87ee6047)

#### 검색 기능

PostRepository
```java
@Query("SELECT p FROM JobPosting p " +
        "WHERE p.company.name LIKE %:keyword% " +
        "OR p.company.country LIKE %:keyword% " +
        "OR p.company.region LIKE %:keyword% " +
        "OR p.jobPosition LIKE %:keyword% " +
        "OR p.recruitmentDetails LIKE %:keyword% " +
        "OR p.technologyUsed LIKE %:keyword% " +
        "OR CAST(p.recruitmentCompensation AS string) LIKE %:keyword%")
List<JobPosting> findBySearchKeyword(String keyword);
```
++23.1018 11:42 수정

PostService

```java
public List<JobPosting> findBySearchKeyword(String keyword) { return postRepository.findBySearchKeyword(keyword);}
```
PostController

```java
// 채용공고 검색
@GetMapping("/search")
public String searchJobPostings(@RequestParam("keyword") String keyword, Model model) {
if (keyword.isEmpty()) {
return "redirect:/jobpostings"; // 검색 키워드가 없는 경우 채용공고 목록 반환
} else {
List<JobPosting> searchResults = postService.findBySearchKeyword(keyword);
model.addAttribute("keyword", keyword);

            if (searchResults.isEmpty()) {
                return "jobpostings/noResults"; // 검색 결과가 없는 경우에 대한 뷰
            } else {
                List<JobPostingDTO> searchResultsDTO = mapToJobPostingDTOList(searchResults);
                model.addAttribute("searchResults", searchResultsDTO);

                return "jobpostings/searchResults"; // 검색 결과가 있는 경우에 대한 뷰
            }
        }
    }
```

![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/c069ef9b-2a0f-4073-bc88-01b813b01c8d)

-**Case 1: 검색어를 포함하는 데이터가 존재하지 않는 경우** 검색 결과 없음 반환

-**Case 2: 검색어를 포함하는 데이터가 존재하는 경우** 검색 결과 반환

-**Case 3: 검색어를 입력하지 않은 경우** 채용공고 목록 반환



#### 기업회원 - 기업 홈화면
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/e57ce02d-00a9-49e4-be95-8068dff2b69a)

기업 ID를 입력 후 [기업 페이지로 이동] 버튼 클릭 시 기업 전용 페이지로 이동

##### 기업 전용 페이지
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/5b20f447-9785-4f06-956f-d07c6e8be610)

- 채용공고 등록 기능
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/db5fe2cd-9697-4995-ab66-2f9cfd52d906)
    companyId는 수정이 불가하다. 등록 후 기업 전용 페이지 반환


- 채용공고 수정 기능
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/829fe340-b945-431b-a74d-a27efbae53e6)
    companyId를 제외한 모든 항목 수정 가능. 수정 후 기업 전용 페이지 반환


- 채용공고 삭제 기능
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/1532f4dd-ce0a-42a6-a859-f6a2f69e4066)
    삭제 시 기업 전용 페이지 반환


#### 일반회원 - 채용공고 목록
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/16efb292-2de7-4179-8600-cf11543034d9)
상세보기 클릭 시 채용공고의 채용 내용 및 해당 회사의 다른 공고를 확인 가능하며, 지원 기능을 제공한다. 

##### 일반회원 - 상세 페이지
다른 공고가 존재하지 않는 경우 "존재하지 않습니다." 메세지를 출력하고, 존재하는 경우 해당 공고의 ID 리스트를 하이퍼링크 형식으로 제공.

Company 엔티티에 getJobPostingIdList 메소드를 생성
```java
public List<Long> getJobPostingIdList() {
List<Long> idList = new ArrayList<>();
for (JobPosting jobPosting : jobPostingList) {
idList.add(jobPosting.getId());
}
return idList;
}
```

PostController에서 상세 정보를 가져오는 메소드를 생성한다.
Company의 getJobPostingIdList로 가져온 리스트에서 현재의 채용공고 ID를 삭제한 후 이를 반환한다.
(리스트가 비어있는 경우 "None" 을 반환)
```java
// 채용공고 상세
@GetMapping("/{id}")
public String retrieveDetails(@PathVariable Long id, Model model) {
Optional<JobPosting> selectedPost = postService.findById(id);
if (!selectedPost.isPresent()) {
throw new UserNotFoundException(String.format("ID[%s] is not found", id));
}
model.addAttribute("jobPosting", selectedPost.get());

        List<Long> jobPostingIdList = selectedPost.get().getCompany().getJobPostingIdList();
        jobPostingIdList.remove(id); // 현재 채용공고 ID를 삭제

        if (jobPostingIdList.isEmpty()) {
            model.addAttribute("anotherPosts", "None");
        } else {
            model.addAttribute("anotherPosts", jobPostingIdList);
        }

        return "jobpostings/postDetails";
    }
```
##### 일반회원 - 지원 기능

PostController의 채용공고 지원 메소드 
```java
// 채용공고 지원
public static final int SUCCESS = 1;
public static final int MEMBER_NOT_FOUND = 2;
public static final int POST_NOT_FOUND = 3;
public static final int ALREADY_APPLIED = 4;

    @PostMapping("/{id}/apply")
    public String applyForJob(@PathVariable Long id, @RequestParam String memberId, Model model) {
        Long applicantId = Long.parseLong(memberId);
        Map<String, Object> response = new HashMap<>();
        int status = SUCCESS;

        if (memberService.findById(applicantId).isEmpty()) {
            status = MEMBER_NOT_FOUND;
        } else {
            Optional<JobPosting> selectedPost = postService.findById(id);
            if (!selectedPost.isPresent()) {
                status = POST_NOT_FOUND;
            } else {
                JobPosting jobPosting = selectedPost.get();
                Member applicant = memberService.findById(applicantId).get();
                if (applicant.getAppliedPosting() != null) {
                    status = ALREADY_APPLIED;
                } else {
                    applicant.setAppliedPosting(jobPosting);
                    memberService.save(applicant);
                    postService.save(jobPosting);
                }
            }
        }
        model.addAttribute("jobPostingId", id);
        model.addAttribute("applicantId", applicantId);
        model.addAttribute("applyStatus", status);
        return "jobpostings/applyResult";
    }
```

![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/217d0a59-2102-4413-aa77-ad88fa51b41c)
Case 5: 채용 공고가 삭제되어 존재하지 않는 경우
![image](https://github.com/yeondori/wanted-pre-onboarding-backend/assets/93027942/33fa5004-2d6a-46ef-af01-2bf9dccf579d)

-**Case 1: 지원이력이 없는 멤버가 지원한 경우** 채용공고와 지원자 ID 반환

-**Case 2: 존재하지 않는 멤버가 지원한 경우** 존재하지 않는 회원 문구 반환

-**Case 3: 지원이력이 있는 멤버가 지원한 경우** 지원불가 문구 반환

-**Case 4: 채용공고가 존재하지 않는 경우** 채용공고 없음 문구 반환


## ⚒️Develop

- 테스트 
- 기업, 사용자 ID 등록 및 인증 절차
- 엔티티 연관관계, 함수 개선
- URI 변수명 통일