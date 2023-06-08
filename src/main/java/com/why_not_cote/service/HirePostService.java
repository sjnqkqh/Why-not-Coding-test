package com.why_not_cote.service;

import com.why_not_cote.config.CommonException;
import com.why_not_cote.dto.hirePost.resp.DetailHirePostRespDto;
import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.repository.HirePostRepository;
import com.why_not_cote.repository.HirePostRepositoryCustom;
import com.why_not_cote.util.CustomObjectUtils;
import com.why_not_cote.util.code.ApiExceptionCode;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HirePostService {

    private final HirePostRepository hirePostRepository;
    private final HirePostRepositoryCustom hirePostRepositoryCustom;


    @Transactional(readOnly = true)
    public List<SearchHirePostRespDto> searchHirePost(List<HirePost> postListByTechStacks,
        List<String> jobCategoryList, YnCode codingTestYn, YnCode assignmentYn) {

        if (CustomObjectUtils.isNotNull(postListByTechStacks) && postListByTechStacks.size() == 0) {
            return new ArrayList<>();
        }

        List<HirePost> result = hirePostRepositoryCustom.getHirePostListToSearch(
            postListByTechStacks, jobCategoryList, codingTestYn, assignmentYn);

        return result.stream().map(SearchHirePostRespDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DetailHirePostRespDto getHirePostDetailDto(Long postId) {
        System.out.println("asfgasfkbafka");
        HirePost hirePost = hirePostRepository.findFirstByPostId(postId).orElseThrow(
            () -> new CommonException(ApiExceptionCode.BAD_REQUEST_EXCEPTION)
        );

        return new DetailHirePostRespDto(hirePost);
    }

    @Transactional(readOnly = true)
    public HirePost getHirePostDetail(Long postId) {
        return hirePostRepository.findById(postId)
            .orElseThrow(() -> new CommonException(ApiExceptionCode.BAD_REQUEST_EXCEPTION));
    }

}
