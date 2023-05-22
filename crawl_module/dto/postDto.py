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
        origin_post_id=None,
    ):
        self.company_name = company_name
        self.post_title = post_title
        self.min_career = min_career
        self.max_career = max_career
        self.techStacks = tech_stacks
        self.locations = locations
        self.tech_stacks = tech_stacks
        self.post_url = post_url
        self._origin_post_id = origin_post_id
        self._post_content = None
        self._education = None
        self._employment_type = None

    @property
    def post_content(self):
        return self._post_content

    @post_content.setter
    def post_content(self, value):
        self._post_content = value

    @property
    def education(self):
        return self._education

    @education.setter
    def education(self, value):
        self._education = value

    @property
    def employment_type(self):
        return self._employment_type

    @employment_type.setter
    def employment_type(self, value):
        self._employment_type = value

    @property
    def origin_post_id(self):
        return self._origin_post_id

    @origin_post_id.setter
    def origin_post_id(self, value):
        self._origin_post_id = value
