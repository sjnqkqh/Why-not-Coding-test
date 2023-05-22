class Post:
    def __init__(
        self,
        company_name,
        post_title,
        min_career,
        max_career,
        tech_stacks,
        locations,
        post_url,
        employment_type=None,
        qualifications_education=None,
    ):
        self.company_name = company_name
        self.post_title = post_title
        self.min_career = min_career
        self.max_career = max_career
        self.techStacks = tech_stacks
        self.employment_type = employment_type
        self.locations = locations
        self.tech_stacks = tech_stacks
        self.post_url = post_url
        self.qualifications_education = qualifications_education
        self.employment_type = employment_type
